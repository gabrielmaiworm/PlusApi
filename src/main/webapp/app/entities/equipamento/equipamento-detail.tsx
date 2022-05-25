import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipamento.reducer';

export const EquipamentoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const equipamentoEntity = useAppSelector(state => state.equipamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipamentoDetailsHeading">Equipamento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{equipamentoEntity.id}</dd>
          <dt>
            <span id="numeroSerie">Numero Serie</span>
          </dt>
          <dd>{equipamentoEntity.numeroSerie}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{equipamentoEntity.nome}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{equipamentoEntity.status}</dd>
          <dt>
            <span id="fotoEquipamento">Foto Equipamento</span>
          </dt>
          <dd>
            {equipamentoEntity.fotoEquipamento ? (
              <div>
                {equipamentoEntity.fotoEquipamentoContentType ? (
                  <a onClick={openFile(equipamentoEntity.fotoEquipamentoContentType, equipamentoEntity.fotoEquipamento)}>
                    <img
                      src={`data:${equipamentoEntity.fotoEquipamentoContentType};base64,${equipamentoEntity.fotoEquipamento}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {equipamentoEntity.fotoEquipamentoContentType}, {byteSize(equipamentoEntity.fotoEquipamento)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/equipamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipamento/${equipamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipamentoDetail;
