import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParceiro } from 'app/shared/model/parceiro.model';
import { getEntities } from './parceiro.reducer';

export const Parceiro = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const parceiroList = useAppSelector(state => state.parceiro.entities);
  const loading = useAppSelector(state => state.parceiro.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="parceiro-heading" data-cy="ParceiroHeading">
        Parceiros
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/parceiro/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Parceiro
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {parceiroList && parceiroList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Razao Social</th>
                <th>Nome Fantasia</th>
                <th>Cnpj</th>
                <th>Inscricao Estadual</th>
                <th>Email</th>
                <th>Telefone</th>
                <th>Tipo Servico</th>
                <th>Nome Gestor</th>
                <th>Sobrenome Gestor</th>
                <th>Documento Gestor</th>
                <th>Telefone Gestor</th>
                <th>Endereco</th>
                <th>Conta Bancaria</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {parceiroList.map((parceiro, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/parceiro/${parceiro.id}`} color="link" size="sm">
                      {parceiro.id}
                    </Button>
                  </td>
                  <td>{parceiro.razaoSocial}</td>
                  <td>{parceiro.nomeFantasia}</td>
                  <td>{parceiro.cnpj}</td>
                  <td>{parceiro.inscricaoEstadual}</td>
                  <td>{parceiro.email}</td>
                  <td>{parceiro.telefone}</td>
                  <td>{parceiro.tipoServico}</td>
                  <td>{parceiro.nomeGestor}</td>
                  <td>{parceiro.sobrenomeGestor}</td>
                  <td>{parceiro.documentoGestor}</td>
                  <td>{parceiro.telefoneGestor}</td>
                  <td>{parceiro.endereco ? <Link to={`/endereco/${parceiro.endereco.id}`}>{parceiro.endereco.id}</Link> : ''}</td>
                  <td>
                    {parceiro.contaBancaria ? (
                      <Link to={`/conta-bancaria/${parceiro.contaBancaria.id}`}>{parceiro.contaBancaria.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{parceiro.user ? parceiro.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/parceiro/${parceiro.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/parceiro/${parceiro.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/parceiro/${parceiro.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Parceiros found</div>
        )}
      </div>
    </div>
  );
};

export default Parceiro;
