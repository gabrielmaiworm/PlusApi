import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './conta-bancaria.reducer';

export const ContaBancariaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contaBancariaEntity = useAppSelector(state => state.contaBancaria.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contaBancariaDetailsHeading">ContaBancaria</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{contaBancariaEntity.id}</dd>
          <dt>
            <span id="banco">Banco</span>
          </dt>
          <dd>{contaBancariaEntity.banco}</dd>
          <dt>
            <span id="agencia">Agencia</span>
          </dt>
          <dd>{contaBancariaEntity.agencia}</dd>
          <dt>
            <span id="numeroConta">Numero Conta</span>
          </dt>
          <dd>{contaBancariaEntity.numeroConta}</dd>
          <dt>
            <span id="tipo">Tipo</span>
          </dt>
          <dd>{contaBancariaEntity.tipo}</dd>
        </dl>
        <Button tag={Link} to="/conta-bancaria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/conta-bancaria/${contaBancariaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContaBancariaDetail;
