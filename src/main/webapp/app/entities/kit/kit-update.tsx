import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBateria } from 'app/shared/model/bateria.model';
import { getEntities as getBaterias } from 'app/entities/bateria/bateria.reducer';
import { IEquipamento } from 'app/shared/model/equipamento.model';
import { getEntities as getEquipamentos } from 'app/entities/equipamento/equipamento.reducer';
import { IKit } from 'app/shared/model/kit.model';
import { StatusKit } from 'app/shared/model/enumerations/status-kit.model';
import { getEntity, updateEntity, createEntity, reset } from './kit.reducer';

export const KitUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const baterias = useAppSelector(state => state.bateria.entities);
  const equipamentos = useAppSelector(state => state.equipamento.entities);
  const kitEntity = useAppSelector(state => state.kit.entity);
  const loading = useAppSelector(state => state.kit.loading);
  const updating = useAppSelector(state => state.kit.updating);
  const updateSuccess = useAppSelector(state => state.kit.updateSuccess);
  const statusKitValues = Object.keys(StatusKit);
  const handleClose = () => {
    props.history.push('/kit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBaterias({}));
    dispatch(getEquipamentos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...kitEntity,
      ...values,
      bateria: baterias.find(it => it.id.toString() === values.bateria.toString()),
      equipamento: equipamentos.find(it => it.id.toString() === values.equipamento.toString()),
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
          statusKit: 'EM_USO',
          ...kitEntity,
          bateria: kitEntity?.bateria?.id,
          equipamento: kitEntity?.equipamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.kit.home.createOrEditLabel" data-cy="KitCreateUpdateHeading">
            Create or edit a Kit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Status Kit" id="kit-statusKit" name="statusKit" data-cy="statusKit" type="select">
                {statusKitValues.map(statusKit => (
                  <option value={statusKit} key={statusKit}>
                    {statusKit}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="kit-bateria" name="bateria" data-cy="bateria" label="Bateria" type="select">
                <option value="" key="0" />
                {baterias
                  ? baterias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="kit-equipamento" name="equipamento" data-cy="equipamento" label="Equipamento" type="select">
                <option value="" key="0" />
                {equipamentos
                  ? equipamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kit" replace color="info">
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

export default KitUpdate;
