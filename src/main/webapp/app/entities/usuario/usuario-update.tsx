import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEndereco } from 'app/shared/model/endereco.model';
import { getEntities as getEnderecos } from 'app/entities/endereco/endereco.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IUsuario } from 'app/shared/model/usuario.model';
import { Lesao } from 'app/shared/model/enumerations/lesao.model';
import { Situacao } from 'app/shared/model/enumerations/situacao.model';
import { Uso } from 'app/shared/model/enumerations/uso.model';
import { getEntity, updateEntity, createEntity, reset } from './usuario.reducer';

export const UsuarioUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const enderecos = useAppSelector(state => state.endereco.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const usuarioEntity = useAppSelector(state => state.usuario.entity);
  const loading = useAppSelector(state => state.usuario.loading);
  const updating = useAppSelector(state => state.usuario.updating);
  const updateSuccess = useAppSelector(state => state.usuario.updateSuccess);
  const lesaoValues = Object.keys(Lesao);
  const situacaoValues = Object.keys(Situacao);
  const usoValues = Object.keys(Uso);
  const handleClose = () => {
    props.history.push('/usuario');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEnderecos({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...usuarioEntity,
      ...values,
      endereco: enderecos.find(it => it.id.toString() === values.endereco.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          lesao: 'PARAPLEGIA',
          situacao: 'TEMPORARIA',
          uso: 'PROPRIO',
          ...usuarioEntity,
          endereco: usuarioEntity?.endereco?.id,
          user: usuarioEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="plusApiApp.usuario.home.createOrEditLabel" data-cy="UsuarioCreateUpdateHeading">
            Create or edit a Usuario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="usuario-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nome" id="usuario-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedField label="Sobrenome" id="usuario-sobrenome" name="sobrenome" data-cy="sobrenome" type="text" />
              <ValidatedField label="Nascimento" id="usuario-nascimento" name="nascimento" data-cy="nascimento" type="date" />
              <ValidatedField label="Documento" id="usuario-documento" name="documento" data-cy="documento" type="text" />
              <ValidatedField label="Lesao" id="usuario-lesao" name="lesao" data-cy="lesao" type="select">
                {lesaoValues.map(lesao => (
                  <option value={lesao} key={lesao}>
                    {lesao}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Situacao" id="usuario-situacao" name="situacao" data-cy="situacao" type="select">
                {situacaoValues.map(situacao => (
                  <option value={situacao} key={situacao}>
                    {situacao}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Uso" id="usuario-uso" name="uso" data-cy="uso" type="select">
                {usoValues.map(uso => (
                  <option value={uso} key={uso}>
                    {uso}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Nome Dependente"
                id="usuario-nomeDependente"
                name="nomeDependente"
                data-cy="nomeDependente"
                type="text"
              />
              <ValidatedField
                label="Nascimento Dependente"
                id="usuario-nascimentoDependente"
                name="nascimentoDependente"
                data-cy="nascimentoDependente"
                type="date"
              />
              <ValidatedField label="Telefone" id="usuario-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField label="Facebook" id="usuario-facebook" name="facebook" data-cy="facebook" type="text" />
              <ValidatedField label="Instagram" id="usuario-instagram" name="instagram" data-cy="instagram" type="text" />
              <ValidatedField label="Termo" id="usuario-termo" name="termo" data-cy="termo" check type="checkbox" />
              <ValidatedField label="Treinamento" id="usuario-treinamento" name="treinamento" data-cy="treinamento" check type="checkbox" />
              <ValidatedBlobField label="Foto Doc" id="usuario-fotoDoc" name="fotoDoc" data-cy="fotoDoc" isImage accept="image/*" />
              <ValidatedBlobField
                label="Foto Com Doc"
                id="usuario-fotoComDoc"
                name="fotoComDoc"
                data-cy="fotoComDoc"
                isImage
                accept="image/*"
              />
              <ValidatedField id="usuario-endereco" name="endereco" data-cy="endereco" label="Endereco" type="select">
                <option value="" key="0" />
                {enderecos
                  ? enderecos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="usuario-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/usuario" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UsuarioUpdate;
