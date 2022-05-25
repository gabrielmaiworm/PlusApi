import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInfoAdicional } from 'app/shared/model/info-adicional.model';
import { getEntities as getInfoAdicionals } from 'app/entities/info-adicional/info-adicional.reducer';
import { IAvaliacao } from 'app/shared/model/avaliacao.model';
import { getEntity, updateEntity, createEntity, reset } from './avaliacao.reducer';

export const AvaliacaoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const infoAdicionals = useAppSelector(state => state.infoAdicional.entities);
  const avaliacaoEntity = useAppSelector(state => state.avaliacao.entity);
  const loading = useAppSelector(state => state.avaliacao.loading);
  const updating = useAppSelector(state => state.avaliacao.updating);
  const updateSuccess = useAppSelector(state => state.avaliacao.updateSuccess);
  const handleClose = () => {
    props.history.push('/avaliacao');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getInfoAdicionals({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...avaliacaoEntity,
      ...values,
      infoAdicional: infoAdicionals.find(it => it.id.toString() === values.infoAdicional.toString()),
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
          ...avaliacaoEntity,
          infoAdicional: avaliacaoEntity?.infoAdicional?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.avaliacao.home.createOrEditLabel" data-cy="AvaliacaoCreateUpdateHeading">
            Create or edit a Avaliacao
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="avaliacao-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Comentario" id="avaliacao-comentario" name="comentario" data-cy="comentario" type="text" />
              <ValidatedField label="Nota" id="avaliacao-nota" name="nota" data-cy="nota" type="text" />
              <ValidatedBlobField label="Imagem" id="avaliacao-imagem" name="imagem" data-cy="imagem" isImage accept="image/*" />
              <ValidatedField
                label="Acesso Entrada Principal"
                id="avaliacao-acessoEntradaPrincipal"
                name="acessoEntradaPrincipal"
                data-cy="acessoEntradaPrincipal"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Acesso Banheiro"
                id="avaliacao-acessoBanheiro"
                name="acessoBanheiro"
                data-cy="acessoBanheiro"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Acesso Audio Visual"
                id="avaliacao-acessoAudioVisual"
                name="acessoAudioVisual"
                data-cy="acessoAudioVisual"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Acesso Circulacao Interna"
                id="avaliacao-acessoCirculacaoInterna"
                name="acessoCirculacaoInterna"
                data-cy="acessoCirculacaoInterna"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Acesso Estacionamento"
                id="avaliacao-acessoEstacionamento"
                name="acessoEstacionamento"
                data-cy="acessoEstacionamento"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Acesso Calcada"
                id="avaliacao-acessoCalcada"
                name="acessoCalcada"
                data-cy="acessoCalcada"
                check
                type="checkbox"
              />
              <ValidatedField
                id="avaliacao-infoAdicional"
                name="infoAdicional"
                data-cy="infoAdicional"
                label="Info Adicional"
                type="select"
              >
                <option value="" key="0" />
                {infoAdicionals
                  ? infoAdicionals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/avaliacao" replace color="info">
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

export default AvaliacaoUpdate;
