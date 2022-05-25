import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './funcionario.reducer';

export const FuncionarioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const funcionarioEntity = useAppSelector(state => state.funcionario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="funcionarioDetailsHeading">Funcionario</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{funcionarioEntity.id}</dd>
          <dt>
            <span id="nivelAcesso">Nivel Acesso</span>
          </dt>
          <dd>{funcionarioEntity.nivelAcesso}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{funcionarioEntity.nome}</dd>
          <dt>
            <span id="sobrenome">Sobrenome</span>
          </dt>
          <dd>{funcionarioEntity.sobrenome}</dd>
          <dt>
            <span id="documento">Documento</span>
          </dt>
          <dd>{funcionarioEntity.documento}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{funcionarioEntity.telefone}</dd>
          <dt>User</dt>
          <dd>{funcionarioEntity.user ? funcionarioEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/funcionario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/funcionario/${funcionarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FuncionarioDetail;
