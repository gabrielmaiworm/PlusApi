import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Usuario from './usuario';
import Equipamento from './equipamento';
import Bateria from './bateria';
import Kit from './kit';
import Parceiro from './parceiro';
import Funcionario from './funcionario';
import Endereco from './endereco';
import ContaBancaria from './conta-bancaria';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}usuario`} component={Usuario} />
        <ErrorBoundaryRoute path={`${match.url}equipamento`} component={Equipamento} />
        <ErrorBoundaryRoute path={`${match.url}bateria`} component={Bateria} />
        <ErrorBoundaryRoute path={`${match.url}kit`} component={Kit} />
        <ErrorBoundaryRoute path={`${match.url}parceiro`} component={Parceiro} />
        <ErrorBoundaryRoute path={`${match.url}funcionario`} component={Funcionario} />
        <ErrorBoundaryRoute path={`${match.url}endereco`} component={Endereco} />
        <ErrorBoundaryRoute path={`${match.url}conta-bancaria`} component={ContaBancaria} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
