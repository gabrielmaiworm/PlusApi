import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './endereco.reducer';

export const EnderecoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const enderecoEntity = useAppSelector(state => state.endereco.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enderecoDetailsHeading">Endereco</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{enderecoEntity.id}</dd>
          <dt>
            <span id="cep">Cep</span>
          </dt>
          <dd>{enderecoEntity.cep}</dd>
          <dt>
            <span id="logradouro">Logradouro</span>
          </dt>
          <dd>{enderecoEntity.logradouro}</dd>
          <dt>
            <span id="numero">Numero</span>
          </dt>
          <dd>{enderecoEntity.numero}</dd>
          <dt>
            <span id="complemento">Complemento</span>
          </dt>
          <dd>{enderecoEntity.complemento}</dd>
          <dt>
            <span id="bairro">Bairro</span>
          </dt>
          <dd>{enderecoEntity.bairro}</dd>
          <dt>
            <span id="cidade">Cidade</span>
          </dt>
          <dd>{enderecoEntity.cidade}</dd>
          <dt>
            <span id="estado">Estado</span>
          </dt>
          <dd>{enderecoEntity.estado}</dd>
        </dl>
        <Button tag={Link} to="/endereco" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/endereco/${enderecoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnderecoDetail;
