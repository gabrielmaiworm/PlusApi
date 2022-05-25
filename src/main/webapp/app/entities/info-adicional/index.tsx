import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InfoAdicional from './info-adicional';
import InfoAdicionalDetail from './info-adicional-detail';
import InfoAdicionalUpdate from './info-adicional-update';
import InfoAdicionalDeleteDialog from './info-adicional-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InfoAdicionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InfoAdicionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InfoAdicionalDetail} />
      <ErrorBoundaryRoute path={match.url} component={InfoAdicional} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InfoAdicionalDeleteDialog} />
  </>
);

export default Routes;
