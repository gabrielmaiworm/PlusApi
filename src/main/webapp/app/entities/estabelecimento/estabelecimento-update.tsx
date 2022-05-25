import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { getEntity, updateEntity, createEntity, reset } from './estabelecimento.reducer';

export const EstabelecimentoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const estabelecimentoEntity = useAppSelector(state => state.estabelecimento.entity);
  const loading = useAppSelector(state => state.estabelecimento.loading);
  const updating = useAppSelector(state => state.estabelecimento.updating);
  const updateSuccess = useAppSelector(state => state.estabelecimento.updateSuccess);
  const handleClose = () => {
    props.history.push('/estabelecimento');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...estabelecimentoEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...estabelecimentoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.estabelecimento.home.createOrEditLabel" data-cy="EstabelecimentoCreateUpdateHeading">
            Create or edit a Estabelecimento
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="estabelecimento-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Endereco" id="estabelecimento-endereco" name="endereco" data-cy="endereco" type="text" />
              <ValidatedField label="Nome" id="estabelecimento-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedField label="Telefone" id="estabelecimento-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField label="Categoria" id="estabelecimento-categoria" name="categoria" data-cy="categoria" type="text" />
              <ValidatedField label="Nota Media" id="estabelecimento-notaMedia" name="notaMedia" data-cy="notaMedia" type="text" />
              <ValidatedField label="Localizacao" id="estabelecimento-localizacao" name="localizacao" data-cy="localizacao" type="text" />
              <ValidatedBlobField label="Imagem" id="estabelecimento-imagem" name="imagem" data-cy="imagem" isImage accept="image/*" />
              <ValidatedField label="Preco Filter" id="estabelecimento-precoFilter" name="precoFilter" data-cy="precoFilter" type="text" />
              <ValidatedField label="Place Id" id="estabelecimento-placeId" name="placeId" data-cy="placeId" type="text" />
              <ValidatedField label="Latitude" id="estabelecimento-latitude" name="latitude" data-cy="latitude" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/estabelecimento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EstabelecimentoUpdate;
