import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Parceiro from './parceiro';
import ParceiroDetail from './parceiro-detail';
import ParceiroUpdate from './parceiro-update';
import ParceiroDeleteDialog from './parceiro-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParceiroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParceiroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParceiroDetail} />
      <ErrorBoundaryRoute path={match.url} component={Parceiro} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParceiroDeleteDialog} />
  </>
);

export default Routes;
