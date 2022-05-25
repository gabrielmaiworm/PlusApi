import { IBateria } from 'app/shared/model/bateria.model';
import { IEquipamento } from 'app/shared/model/equipamento.model';
import { StatusKit } from 'app/shared/model/enumerations/status-kit.model';

export interface IKit {
  id?: number;
  statusKit?: StatusKit | null;
  bateria?: IBateria | null;
  equipamento?: IEquipamento | null;
}

export const defaultValue: Readonly<IKit> = {};
