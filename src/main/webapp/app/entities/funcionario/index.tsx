import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Funcionario from './funcionario';
import FuncionarioDetail from './funcionario-detail';
import FuncionarioUpdate from './funcionario-update';
import FuncionarioDeleteDialog from './funcionario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FuncionarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FuncionarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FuncionarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Funcionario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FuncionarioDeleteDialog} />
  </>
);

export default Routes;
