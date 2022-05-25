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
import { IContaBancaria } from 'app/shared/model/conta-bancaria.model';
import { Tipo } from 'app/shared/model/enumerations/tipo.model';
import { getEntity, updateEntity, createEntity, reset } from './conta-bancaria.reducer';

export const ContaBancariaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const parceiros = useAppSelector(state => state.parceiro.entities);
  const contaBancariaEntity = useAppSelector(state => state.contaBancaria.entity);
  const loading = useAppSelector(state => state.contaBancaria.loading);
  const updating = useAppSelector(state => state.contaBancaria.updating);
  const updateSuccess = useAppSelector(state => state.contaBancaria.updateSuccess);
  const tipoValues = Object.keys(Tipo);
  const handleClose = () => {
    props.history.push('/conta-bancaria');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getParceiros({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contaBancariaEntity,
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
          tipo: 'CONTA_CORRENTE',
          ...contaBancariaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.contaBancaria.home.createOrEditLabel" data-cy="ContaBancariaCreateUpdateHeading">
            Create or edit a ContaBancaria
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
                <ValidatedField name="id" required readOnly id="conta-bancaria-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Banco" id="conta-bancaria-banco" name="banco" data-cy="banco" type="text" />
              <ValidatedField label="Agencia" id="conta-bancaria-agencia" name="agencia" data-cy="agencia" type="text" />
              <ValidatedField label="Numero Conta" id="conta-bancaria-numeroConta" name="numeroConta" data-cy="numeroConta" type="text" />
              <ValidatedField label="Tipo" id="conta-bancaria-tipo" name="tipo" data-cy="tipo" type="select">
                {tipoValues.map(tipo => (
                  <option value={tipo} key={tipo}>
                    {tipo}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/conta-bancaria" replace color="info">
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

export default ContaBancariaUpdate;
