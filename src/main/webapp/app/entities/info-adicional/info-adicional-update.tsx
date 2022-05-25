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
import { IInfoAdicional } from 'app/shared/model/info-adicional.model';
import { Deficiencia } from 'app/shared/model/enumerations/deficiencia.model';
import { getEntity, updateEntity, createEntity, reset } from './info-adicional.reducer';

export const InfoAdicionalUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const infoAdicionalEntity = useAppSelector(state => state.infoAdicional.entity);
  const loading = useAppSelector(state => state.infoAdicional.loading);
  const updating = useAppSelector(state => state.infoAdicional.updating);
  const updateSuccess = useAppSelector(state => state.infoAdicional.updateSuccess);
  const deficienciaValues = Object.keys(Deficiencia);
  const handleClose = () => {
    props.history.push('/info-adicional');
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
      ...infoAdicionalEntity,
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
          deficiencia: 'AUDITIVA',
          ...infoAdicionalEntity,
          user: infoAdicionalEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.infoAdicional.home.createOrEditLabel" data-cy="InfoAdicionalCreateUpdateHeading">
            Create or edit a InfoAdicional
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
                <ValidatedField name="id" required readOnly id="info-adicional-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Nome" id="info-adicional-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedField label="Cpf" id="info-adicional-cpf" name="cpf" data-cy="cpf" type="text" />
              <ValidatedField label="Telefone" id="info-adicional-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField label="Deficiencia" id="info-adicional-deficiencia" name="deficiencia" data-cy="deficiencia" type="select">
                {deficienciaValues.map(deficiencia => (
                  <option value={deficiencia} key={deficiencia}>
                    {deficiencia}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="info-adicional-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/info-adicional" replace color="info">
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

export default InfoAdicionalUpdate;
