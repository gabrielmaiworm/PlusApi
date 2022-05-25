import usuario from 'app/entities/usuario/usuario.reducer';
import equipamento from 'app/entities/equipamento/equipamento.reducer';
import bateria from 'app/entities/bateria/bateria.reducer';
import kit from 'app/entities/kit/kit.reducer';
import parceiro from 'app/entities/parceiro/parceiro.reducer';
import funcionario from 'app/entities/funcionario/funcionario.reducer';
import endereco from 'app/entities/endereco/endereco.reducer';
import contaBancaria from 'app/entities/conta-bancaria/conta-bancaria.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  usuario,
  equipamento,
  bateria,
  kit,
  parceiro,
  funcionario,
  endereco,
  contaBancaria,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
