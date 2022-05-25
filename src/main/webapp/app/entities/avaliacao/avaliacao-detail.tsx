import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './avaliacao.reducer';

export const AvaliacaoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const avaliacaoEntity = useAppSelector(state => state.avaliacao.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="avaliacaoDetailsHeading">Avaliacao</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{avaliacaoEntity.id}</dd>
          <dt>
            <span id="comentario">Comentario</span>
          </dt>
          <dd>{avaliacaoEntity.comentario}</dd>
          <dt>
            <span id="nota">Nota</span>
          </dt>
          <dd>{avaliacaoEntity.nota}</dd>
          <dt>
            <span id="imagem">Imagem</span>
          </dt>
          <dd>
            {avaliacaoEntity.imagem ? (
              <div>
                {avaliacaoEntity.imagemContentType ? (
                  <a onClick={openFile(avaliacaoEntity.imagemContentType, avaliacaoEntity.imagem)}>
                    <img src={`data:${avaliacaoEntity.imagemContentType};base64,${avaliacaoEntity.imagem}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {avaliacaoEntity.imagemContentType}, {byteSize(avaliacaoEntity.imagem)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="acessoEntradaPrincipal">Acesso Entrada Principal</span>
          </dt>
          <dd>{avaliacaoEntity.acessoEntradaPrincipal ? 'true' : 'false'}</dd>
          <dt>
            <span id="acessoBanheiro">Acesso Banheiro</span>
          </dt>
          <dd>{avaliacaoEntity.acessoBanheiro ? 'true' : 'false'}</dd>
          <dt>
            <span id="acessoAudioVisual">Acesso Audio Visual</span>
          </dt>
          <dd>{avaliacaoEntity.acessoAudioVisual ? 'true' : 'false'}</dd>
          <dt>
            <span id="acessoCirculacaoInterna">Acesso Circulacao Interna</span>
          </dt>
          <dd>{avaliacaoEntity.acessoCirculacaoInterna ? 'true' : 'false'}</dd>
          <dt>
            <span id="acessoEstacionamento">Acesso Estacionamento</span>
          </dt>
          <dd>{avaliacaoEntity.acessoEstacionamento ? 'true' : 'false'}</dd>
          <dt>
            <span id="acessoCalcada">Acesso Calcada</span>
          </dt>
          <dd>{avaliacaoEntity.acessoCalcada ? 'true' : 'false'}</dd>
          <dt>Info Adicional</dt>
          <dd>{avaliacaoEntity.infoAdicional ? avaliacaoEntity.infoAdicional.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/avaliacao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/avaliacao/${avaliacaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AvaliacaoDetail;
