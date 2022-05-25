import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './estabelecimento.reducer';

export const EstabelecimentoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const estabelecimentoEntity = useAppSelector(state => state.estabelecimento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="estabelecimentoDetailsHeading">Estabelecimento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{estabelecimentoEntity.id}</dd>
          <dt>
            <span id="endereco">Endereco</span>
          </dt>
          <dd>{estabelecimentoEntity.endereco}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{estabelecimentoEntity.nome}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{estabelecimentoEntity.telefone}</dd>
          <dt>
            <span id="categoria">Categoria</span>
          </dt>
          <dd>{estabelecimentoEntity.categoria}</dd>
          <dt>
            <span id="notaMedia">Nota Media</span>
          </dt>
          <dd>{estabelecimentoEntity.notaMedia}</dd>
          <dt>
            <span id="localizacao">Localizacao</span>
          </dt>
          <dd>{estabelecimentoEntity.localizacao}</dd>
          <dt>
            <span id="imagem">Imagem</span>
          </dt>
          <dd>
            {estabelecimentoEntity.imagem ? (
              <div>
                {estabelecimentoEntity.imagemContentType ? (
                  <a onClick={openFile(estabelecimentoEntity.imagemContentType, estabelecimentoEntity.imagem)}>
                    <img
                      src={`data:${estabelecimentoEntity.imagemContentType};base64,${estabelecimentoEntity.imagem}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {estabelecimentoEntity.imagemContentType}, {byteSize(estabelecimentoEntity.imagem)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="precoFilter">Preco Filter</span>
          </dt>
          <dd>{estabelecimentoEntity.precoFilter}</dd>
          <dt>
            <span id="placeId">Place Id</span>
          </dt>
          <dd>{estabelecimentoEntity.placeId}</dd>
          <dt>
            <span id="latitude">Latitude</span>
          </dt>
          <dd>{estabelecimentoEntity.latitude}</dd>
          <dt>
            <span id="longitude">Longitude</span>
          </dt>
          <dd>{estabelecimentoEntity.longitude}</dd>
        </dl>
        <Button tag={Link} to="/estabelecimento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/estabelecimento/${estabelecimentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EstabelecimentoDetail;
