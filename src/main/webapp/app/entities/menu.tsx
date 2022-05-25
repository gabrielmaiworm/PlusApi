import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/usuario">
        Usuario
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipamento">
        Equipamento
      </MenuItem>
      <MenuItem icon="asterisk" to="/bateria">
        Bateria
      </MenuItem>
      <MenuItem icon="asterisk" to="/kit">
        Kit
      </MenuItem>
      <MenuItem icon="asterisk" to="/parceiro">
        Parceiro
      </MenuItem>
      <MenuItem icon="asterisk" to="/funcionario">
        Funcionario
      </MenuItem>
      <MenuItem icon="asterisk" to="/endereco">
        Endereco
      </MenuItem>
      <MenuItem icon="asterisk" to="/conta-bancaria">
        Conta Bancaria
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
