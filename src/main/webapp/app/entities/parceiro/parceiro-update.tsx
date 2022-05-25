import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEndereco } from 'app/shared/model/endereco.model';
import { getEntities as getEnderecos } from 'app/entities/endereco/endereco.reducer';
import { IContaBancaria } from 'app/shared/model/conta-bancaria.model';
import { getEntities as getContaBancarias } from 'app/entities/conta-bancaria/conta-bancaria.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IParceiro } from 'app/shared/model/parceiro.model';
import { getEntity, updateEntity, createEntity, reset } from './parceiro.reducer';

export const ParceiroUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const enderecos = useAppSelector(state => state.endereco.entities);
  const contaBancarias = useAppSelector(state => state.contaBancaria.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const parceiroEntity = useAppSelector(state => state.parceiro.entity);
  const loading = useAppSelector(state => state.parceiro.loading);
  const updating = useAppSelector(state => state.parceiro.updating);
  const updateSuccess = useAppSelector(state => state.parceiro.updateSuccess);
  const handleClose = () => {
    props.history.push('/parceiro');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEnderecos({}));
    dispatch(getContaBancarias({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...parceiroEntity,
      ...values,
      endereco: enderecos.find(it => it.id.toString() === values.endereco.toString()),
      contaBancaria: contaBancarias.find(it => it.id.toString() === values.contaBancaria.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...parceiroEntity,
          endereco: parceiroEntity?.endereco?.id,
          contaBancaria: parceiroEntity?.contaBancaria?.id,
          user: parceiroEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.parceiro.home.createOrEditLabel" data-cy="ParceiroCreateUpdateHeading">
            Create or edit a Parceiro
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="parceiro-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Razao Social" id="parceiro-razaoSocial" name="razaoSocial" data-cy="razaoSocial" type="text" />
              <ValidatedField label="Nome Fantasia" id="parceiro-nomeFantasia" name="nomeFantasia" data-cy="nomeFantasia" type="text" />
              <ValidatedField label="Cnpj" id="parceiro-cnpj" name="cnpj" data-cy="cnpj" type="text" />
              <ValidatedField
                label="Inscricao Estadual"
                id="parceiro-inscricaoEstadual"
                name="inscricaoEstadual"
                data-cy="inscricaoEstadual"
                type="text"
              />
              <ValidatedField label="Email" id="parceiro-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Telefone" id="parceiro-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField label="Tipo Servico" id="parceiro-tipoServico" name="tipoServico" data-cy="tipoServico" type="text" />
              <ValidatedField label="Nome Gestor" id="parceiro-nomeGestor" name="nomeGestor" data-cy="nomeGestor" type="text" />
              <ValidatedField
                label="Sobrenome Gestor"
                id="parceiro-sobrenomeGestor"
                name="sobrenomeGestor"
                data-cy="sobrenomeGestor"
                type="text"
              />
              <ValidatedField
                label="Documento Gestor"
                id="parceiro-documentoGestor"
                name="documentoGestor"
                data-cy="documentoGestor"
                type="text"
              />
              <ValidatedField
                label="Telefone Gestor"
                id="parceiro-telefoneGestor"
                name="telefoneGestor"
                data-cy="telefoneGestor"
                type="text"
              />
              <ValidatedField id="parceiro-endereco" name="endereco" data-cy="endereco" label="Endereco" type="select">
                <option value="" key="0" />
                {enderecos
                  ? enderecos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="parceiro-contaBancaria" name="contaBancaria" data-cy="contaBancaria" label="Conta Bancaria" type="select">
                <option value="" key="0" />
                {contaBancarias
                  ? contaBancarias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="parceiro-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parceiro" replace color="info">
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

export default ParceiroUpdate;
