import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInfoAdicional } from 'app/shared/model/info-adicional.model';
import { getEntities } from './info-adicional.reducer';

export const InfoAdicional = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const infoAdicionalList = useAppSelector(state => state.infoAdicional.entities);
  const loading = useAppSelector(state => state.infoAdicional.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="info-adicional-heading" data-cy="InfoAdicionalHeading">
        Info Adicionals
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/info-adicional/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Info Adicional
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {infoAdicionalList && infoAdicionalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Cpf</th>
                <th>Telefone</th>
                <th>Deficiencia</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {infoAdicionalList.map((infoAdicional, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/info-adicional/${infoAdicional.id}`} color="link" size="sm">
                      {infoAdicional.id}
                    </Button>
                  </td>
                  <td>{infoAdicional.nome}</td>
                  <td>{infoAdicional.cpf}</td>
                  <td>{infoAdicional.telefone}</td>
                  <td>{infoAdicional.deficiencia}</td>
                  <td>{infoAdicional.user ? infoAdicional.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/info-adicional/${infoAdicional.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/info-adicional/${infoAdicional.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/info-adicional/${infoAdicional.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Info Adicionals found</div>
        )}
      </div>
    </div>
  );
};

export default InfoAdicional;
