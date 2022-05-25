import { IAvaliacao } from 'app/shared/model/avaliacao.model';
import { IUser } from 'app/shared/model/user.model';
import { Deficiencia } from 'app/shared/model/enumerations/deficiencia.model';

export interface IInfoAdicional {
  id?: number;
  nome?: string | null;
  cpf?: string | null;
  telefone?: string | null;
  deficiencia?: Deficiencia | null;
  avaliacaos?: IAvaliacao[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IInfoAdicional> = {};
