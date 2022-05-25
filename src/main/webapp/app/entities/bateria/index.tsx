import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bateria from './bateria';
import BateriaDetail from './bateria-detail';
import BateriaUpdate from './bateria-update';
import BateriaDeleteDialog from './bateria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BateriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BateriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BateriaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bateria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BateriaDeleteDialog} />
  </>
);

export default Routes;
