import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParceiro } from 'app/shared/model/parceiro.model';
import { getEntities as getParceiros } from 'app/entities/parceiro/parceiro.reducer';
import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntities as getUsuarios } from 'app/entities/usuario/usuario.reducer';
import { IEndereco } from 'app/shared/model/endereco.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';
import { getEntity, updateEntity, createEntity, reset } from './endereco.reducer';

export const EnderecoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const parceiros = useAppSelector(state => state.parceiro.entities);
  const usuarios = useAppSelector(state => state.usuario.entities);
  const enderecoEntity = useAppSelector(state => state.endereco.entity);
  const loading = useAppSelector(state => state.endereco.loading);
  const updating = useAppSelector(state => state.endereco.updating);
  const updateSuccess = useAppSelector(state => state.endereco.updateSuccess);
  const estadoValues = Object.keys(Estado);
  const handleClose = () => {
    props.history.push('/endereco');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getParceiros({}));
    dispatch(getUsuarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...enderecoEntity,
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
          estado: 'AC',
          ...enderecoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.endereco.home.createOrEditLabel" data-cy="EnderecoCreateUpdateHeading">
            Create or edit a Endereco
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="endereco-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Cep" id="endereco-cep" name="cep" data-cy="cep" type="text" />
              <ValidatedField label="Logradouro" id="endereco-logradouro" name="logradouro" data-cy="logradouro" type="text" />
              <ValidatedField label="Numero" id="endereco-numero" name="numero" data-cy="numero" type="text" />
              <ValidatedField label="Complemento" id="endereco-complemento" name="complemento" data-cy="complemento" type="text" />
              <ValidatedField label="Bairro" id="endereco-bairro" name="bairro" data-cy="bairro" type="text" />
              <ValidatedField label="Cidade" id="endereco-cidade" name="cidade" data-cy="cidade" type="text" />
              <ValidatedField label="Estado" id="endereco-estado" name="estado" data-cy="estado" type="select">
                {estadoValues.map(estado => (
                  <option value={estado} key={estado}>
                    {estado}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/endereco" replace color="info">
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

export default EnderecoUpdate;
