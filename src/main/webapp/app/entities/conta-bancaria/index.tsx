import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContaBancaria from './conta-bancaria';
import ContaBancariaDetail from './conta-bancaria-detail';
import ContaBancariaUpdate from './conta-bancaria-update';
import ContaBancariaDeleteDialog from './conta-bancaria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContaBancariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContaBancariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContaBancariaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContaBancaria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContaBancariaDeleteDialog} />
  </>
);

export default Routes;
