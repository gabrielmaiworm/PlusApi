import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { getEntities } from './estabelecimento.reducer';

export const Estabelecimento = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const estabelecimentoList = useAppSelector(state => state.estabelecimento.entities);
  const loading = useAppSelector(state => state.estabelecimento.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="estabelecimento-heading" data-cy="EstabelecimentoHeading">
        Estabelecimentos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/estabelecimento/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Estabelecimento
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {estabelecimentoList && estabelecimentoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Endereco</th>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Categoria</th>
                <th>Nota Media</th>
                <th>Localizacao</th>
                <th>Imagem</th>
                <th>Preco Filter</th>
                <th>Place Id</th>
                <th>Latitude</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {estabelecimentoList.map((estabelecimento, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/estabelecimento/${estabelecimento.id}`} color="link" size="sm">
                      {estabelecimento.id}
                    </Button>
                  </td>
                  <td>{estabelecimento.endereco}</td>
                  <td>{estabelecimento.nome}</td>
                  <td>{estabelecimento.telefone}</td>
                  <td>{estabelecimento.categoria}</td>
                  <td>{estabelecimento.notaMedia}</td>
                  <td>{estabelecimento.localizacao}</td>
                  <td>
                    {estabelecimento.imagem ? (
                      <div>
                        {estabelecimento.imagemContentType ? (
                          <a onClick={openFile(estabelecimento.imagemContentType, estabelecimento.imagem)}>
                            <img
                              src={`data:${estabelecimento.imagemContentType};base64,${estabelecimento.imagem}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {estabelecimento.imagemContentType}, {byteSize(estabelecimento.imagem)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{estabelecimento.precoFilter}</td>
                  <td>{estabelecimento.placeId}</td>
                  <td>{estabelecimento.latitude}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/estabelecimento/${estabelecimento.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/estabelecimento/${estabelecimento.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/estabelecimento/${estabelecimento.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Estabelecimentos found</div>
        )}
      </div>
    </div>
  );
};

export default Estabelecimento;
