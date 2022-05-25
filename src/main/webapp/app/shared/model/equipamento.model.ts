import { IKit } from 'app/shared/model/kit.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IEquipamento {
  id?: number;
  numeroSerie?: number | null;
  nome?: string | null;
  status?: Status | null;
  fotoEquipamentoContentType?: string | null;
  fotoEquipamento?: string | null;
  kit?: IKit | null;
}

export const defaultValue: Readonly<IEquipamento> = {};
