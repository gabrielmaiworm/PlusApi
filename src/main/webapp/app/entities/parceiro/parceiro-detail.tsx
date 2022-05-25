import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parceiro.reducer';

export const ParceiroDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const parceiroEntity = useAppSelector(state => state.parceiro.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parceiroDetailsHeading">Parceiro</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{parceiroEntity.id}</dd>
          <dt>
            <span id="razaoSocial">Razao Social</span>
          </dt>
          <dd>{parceiroEntity.razaoSocial}</dd>
          <dt>
            <span id="nomeFantasia">Nome Fantasia</span>
          </dt>
          <dd>{parceiroEntity.nomeFantasia}</dd>
          <dt>
            <span id="cnpj">Cnpj</span>
          </dt>
          <dd>{parceiroEntity.cnpj}</dd>
          <dt>
            <span id="inscricaoEstadual">Inscricao Estadual</span>
          </dt>
          <dd>{parceiroEntity.inscricaoEstadual}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{parceiroEntity.email}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{parceiroEntity.telefone}</dd>
          <dt>
            <span id="tipoServico">Tipo Servico</span>
          </dt>
          <dd>{parceiroEntity.tipoServico}</dd>
          <dt>
            <span id="nomeGestor">Nome Gestor</span>
          </dt>
          <dd>{parceiroEntity.nomeGestor}</dd>
          <dt>
            <span id="sobrenomeGestor">Sobrenome Gestor</span>
          </dt>
          <dd>{parceiroEntity.sobrenomeGestor}</dd>
          <dt>
            <span id="documentoGestor">Documento Gestor</span>
          </dt>
          <dd>{parceiroEntity.documentoGestor}</dd>
          <dt>
            <span id="telefoneGestor">Telefone Gestor</span>
          </dt>
          <dd>{parceiroEntity.telefoneGestor}</dd>
          <dt>Endereco</dt>
          <dd>{parceiroEntity.endereco ? parceiroEntity.endereco.id : ''}</dd>
          <dt>Conta Bancaria</dt>
          <dd>{parceiroEntity.contaBancaria ? parceiroEntity.contaBancaria.id : ''}</dd>
          <dt>User</dt>
          <dd>{parceiroEntity.user ? parceiroEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parceiro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parceiro/${parceiroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParceiroDetail;
