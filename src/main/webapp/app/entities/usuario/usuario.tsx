import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntities } from './usuario.reducer';

export const Usuario = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const usuarioList = useAppSelector(state => state.usuario.entities);
  const loading = useAppSelector(state => state.usuario.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="usuario-heading" data-cy="UsuarioHeading">
        Usuarios
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/usuario/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Usuario
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {usuarioList && usuarioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Sobrenome</th>
                <th>Nascimento</th>
                <th>Documento</th>
                <th>Lesao</th>
                <th>Situacao</th>
                <th>Uso</th>
                <th>Nome Dependente</th>
                <th>Nascimento Dependente</th>
                <th>Telefone</th>
                <th>Facebook</th>
                <th>Instagram</th>
                <th>Termo</th>
                <th>Treinamento</th>
                <th>Foto Doc</th>
                <th>Foto Com Doc</th>
                <th>Endereco</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {usuarioList.map((usuario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/usuario/${usuario.id}`} color="link" size="sm">
                      {usuario.id}
                    </Button>
                  </td>
                  <td>{usuario.nome}</td>
                  <td>{usuario.sobrenome}</td>
                  <td>
                    {usuario.nascimento ? <TextFormat type="date" value={usuario.nascimento} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{usuario.documento}</td>
                  <td>{usuario.lesao}</td>
                  <td>{usuario.situacao}</td>
                  <td>{usuario.uso}</td>
                  <td>{usuario.nomeDependente}</td>
                  <td>
                    {usuario.nascimentoDependente ? (
                      <TextFormat type="date" value={usuario.nascimentoDependente} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{usuario.telefone}</td>
                  <td>{usuario.facebook}</td>
                  <td>{usuario.instagram}</td>
                  <td>{usuario.termo ? 'true' : 'false'}</td>
                  <td>{usuario.treinamento ? 'true' : 'false'}</td>
                  <td>
                    {usuario.fotoDoc ? (
                      <div>
                        {usuario.fotoDocContentType ? (
                          <a onClick={openFile(usuario.fotoDocContentType, usuario.fotoDoc)}>
                            <img src={`data:${usuario.fotoDocContentType};base64,${usuario.fotoDoc}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {usuario.fotoDocContentType}, {byteSize(usuario.fotoDoc)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {usuario.fotoComDoc ? (
                      <div>
                        {usuario.fotoComDocContentType ? (
                          <a onClick={openFile(usuario.fotoComDocContentType, usuario.fotoComDoc)}>
                            <img src={`data:${usuario.fotoComDocContentType};base64,${usuario.fotoComDoc}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {usuario.fotoComDocContentType}, {byteSize(usuario.fotoComDoc)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{usuario.endereco ? <Link to={`/endereco/${usuario.endereco.id}`}>{usuario.endereco.id}</Link> : ''}</td>
                  <td>{usuario.user ? usuario.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/usuario/${usuario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/usuario/${usuario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/usuario/${usuario.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Usuarios found</div>
        )}
      </div>
    </div>
  );
};

export default Usuario;
