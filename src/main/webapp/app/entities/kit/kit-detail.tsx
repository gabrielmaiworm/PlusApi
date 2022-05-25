import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kit.reducer';

export const KitDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const kitEntity = useAppSelector(state => state.kit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kitDetailsHeading">Kit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kitEntity.id}</dd>
          <dt>
            <span id="statusKit">Status Kit</span>
          </dt>
          <dd>{kitEntity.statusKit}</dd>
          <dt>Bateria</dt>
          <dd>{kitEntity.bateria ? kitEntity.bateria.id : ''}</dd>
          <dt>Equipamento</dt>
          <dd>{kitEntity.equipamento ? kitEntity.equipamento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/kit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kit/${kitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KitDetail;
