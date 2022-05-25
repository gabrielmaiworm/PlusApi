import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKit } from 'app/shared/model/kit.model';
import { getEntities } from './kit.reducer';

export const Kit = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const kitList = useAppSelector(state => state.kit.entities);
  const loading = useAppSelector(state => state.kit.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="kit-heading" data-cy="KitHeading">
        Kits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/kit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Kit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kitList && kitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Status Kit</th>
                <th>Bateria</th>
                <th>Equipamento</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kitList.map((kit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kit/${kit.id}`} color="link" size="sm">
                      {kit.id}
                    </Button>
                  </td>
                  <td>{kit.statusKit}</td>
                  <td>{kit.bateria ? <Link to={`/bateria/${kit.bateria.id}`}>{kit.bateria.id}</Link> : ''}</td>
                  <td>{kit.equipamento ? <Link to={`/equipamento/${kit.equipamento.id}`}>{kit.equipamento.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/kit/${kit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/kit/${kit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/kit/${kit.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Kits found</div>
        )}
      </div>
    </div>
  );
};

export default Kit;
