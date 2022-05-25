import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Equipamento from './equipamento';
import EquipamentoDetail from './equipamento-detail';
import EquipamentoUpdate from './equipamento-update';
import EquipamentoDeleteDialog from './equipamento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EquipamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EquipamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EquipamentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Equipamento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EquipamentoDeleteDialog} />
  </>
);

export default Routes;
