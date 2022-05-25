import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Kit from './kit';
import KitDetail from './kit-detail';
import KitUpdate from './kit-update';
import KitDeleteDialog from './kit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KitDetail} />
      <ErrorBoundaryRoute path={match.url} component={Kit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KitDeleteDialog} />
  </>
);

export default Routes;
