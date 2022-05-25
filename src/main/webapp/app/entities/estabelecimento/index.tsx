import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Estabelecimento from './estabelecimento';
import EstabelecimentoDetail from './estabelecimento-detail';
import EstabelecimentoUpdate from './estabelecimento-update';
import EstabelecimentoDeleteDialog from './estabelecimento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EstabelecimentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EstabelecimentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EstabelecimentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Estabelecimento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EstabelecimentoDeleteDialog} />
  </>
);

export default Routes;
