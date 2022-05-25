import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { NivelAcesso } from 'app/shared/model/enumerations/nivel-acesso.model';
import { getEntity, updateEntity, createEntity, reset } from './funcionario.reducer';

export const FuncionarioUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const funcionarioEntity = useAppSelector(state => state.funcionario.entity);
  const loading = useAppSelector(state => state.funcionario.loading);
  const updating = useAppSelector(state => state.funcionario.updating);
  const updateSuccess = useAppSelector(state => state.funcionario.updateSuccess);
  const nivelAcessoValues = Object.keys(NivelAcesso);
  const handleClose = () => {
    props.history.push('/funcionario');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...funcionarioEntity,
      ...values,
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
          nivelAcesso: 'ADMINISTRADOR',
          ...funcionarioEntity,
          user: funcionarioEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.funcionario.home.createOrEditLabel" data-cy="FuncionarioCreateUpdateHeading">
            Create or edit a Funcionario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="funcionario-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nivel Acesso" id="funcionario-nivelAcesso" name="nivelAcesso" data-cy="nivelAcesso" type="select">
                {nivelAcessoValues.map(nivelAcesso => (
                  <option value={nivelAcesso} key={nivelAcesso}>
                    {nivelAcesso}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Nome" id="funcionario-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedField label="Sobrenome" id="funcionario-sobrenome" name="sobrenome" data-cy="sobrenome" type="text" />
              <ValidatedField label="Documento" id="funcionario-documento" name="documento" data-cy="documento" type="text" />
              <ValidatedField label="Telefone" id="funcionario-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField id="funcionario-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/funcionario" replace color="info">
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

export default FuncionarioUpdate;
