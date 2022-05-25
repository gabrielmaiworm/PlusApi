import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './info-adicional.reducer';

export const InfoAdicionalDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const infoAdicionalEntity = useAppSelector(state => state.infoAdicional.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="infoAdicionalDetailsHeading">InfoAdicional</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{infoAdicionalEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{infoAdicionalEntity.nome}</dd>
          <dt>
            <span id="cpf">Cpf</span>
          </dt>
          <dd>{infoAdicionalEntity.cpf}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{infoAdicionalEntity.telefone}</dd>
          <dt>
            <span id="deficiencia">Deficiencia</span>
          </dt>
          <dd>{infoAdicionalEntity.deficiencia}</dd>
          <dt>User</dt>
          <dd>{infoAdicionalEntity.user ? infoAdicionalEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/info-adicional" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/info-adicional/${infoAdicionalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InfoAdicionalDetail;
