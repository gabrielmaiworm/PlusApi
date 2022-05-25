import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './usuario.reducer';

export const UsuarioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const usuarioEntity = useAppSelector(state => state.usuario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usuarioDetailsHeading">Usuario</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{usuarioEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{usuarioEntity.nome}</dd>
          <dt>
            <span id="sobrenome">Sobrenome</span>
          </dt>
          <dd>{usuarioEntity.sobrenome}</dd>
          <dt>
            <span id="nascimento">Nascimento</span>
          </dt>
          <dd>
            {usuarioEntity.nascimento ? <TextFormat value={usuarioEntity.nascimento} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="documento">Documento</span>
          </dt>
          <dd>{usuarioEntity.documento}</dd>
          <dt>
            <span id="lesao">Lesao</span>
          </dt>
          <dd>{usuarioEntity.lesao}</dd>
          <dt>
            <span id="situacao">Situacao</span>
          </dt>
          <dd>{usuarioEntity.situacao}</dd>
          <dt>
            <span id="uso">Uso</span>
          </dt>
          <dd>{usuarioEntity.uso}</dd>
          <dt>
            <span id="nomeDependente">Nome Dependente</span>
          </dt>
          <dd>{usuarioEntity.nomeDependente}</dd>
          <dt>
            <span id="nascimentoDependente">Nascimento Dependente</span>
          </dt>
          <dd>
            {usuarioEntity.nascimentoDependente ? (
              <TextFormat value={usuarioEntity.nascimentoDependente} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{usuarioEntity.telefone}</dd>
          <dt>
            <span id="facebook">Facebook</span>
          </dt>
          <dd>{usuarioEntity.facebook}</dd>
          <dt>
            <span id="instagram">Instagram</span>
          </dt>
          <dd>{usuarioEntity.instagram}</dd>
          <dt>
            <span id="termo">Termo</span>
          </dt>
          <dd>{usuarioEntity.termo ? 'true' : 'false'}</dd>
          <dt>
            <span id="treinamento">Treinamento</span>
          </dt>
          <dd>{usuarioEntity.treinamento ? 'true' : 'false'}</dd>
          <dt>
            <span id="fotoDoc">Foto Doc</span>
          </dt>
          <dd>
            {usuarioEntity.fotoDoc ? (
              <div>
                {usuarioEntity.fotoDocContentType ? (
                  <a onClick={openFile(usuarioEntity.fotoDocContentType, usuarioEntity.fotoDoc)}>
                    <img src={`data:${usuarioEntity.fotoDocContentType};base64,${usuarioEntity.fotoDoc}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {usuarioEntity.fotoDocContentType}, {byteSize(usuarioEntity.fotoDoc)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="fotoComDoc">Foto Com Doc</span>
          </dt>
          <dd>
            {usuarioEntity.fotoComDoc ? (
              <div>
                {usuarioEntity.fotoComDocContentType ? (
                  <a onClick={openFile(usuarioEntity.fotoComDocContentType, usuarioEntity.fotoComDoc)}>
                    <img
                      src={`data:${usuarioEntity.fotoComDocContentType};base64,${usuarioEntity.fotoComDoc}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {usuarioEntity.fotoComDocContentType}, {byteSize(usuarioEntity.fotoComDoc)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Endereco</dt>
          <dd>{usuarioEntity.endereco ? usuarioEntity.endereco.id : ''}</dd>
          <dt>User</dt>
          <dd>{usuarioEntity.user ? usuarioEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/usuario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/usuario/${usuarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UsuarioDetail;
