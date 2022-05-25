import { IUser } from 'app/shared/model/user.model';
import { NivelAcesso } from 'app/shared/model/enumerations/nivel-acesso.model';

export interface IFuncionario {
  id?: number;
  nivelAcesso?: NivelAcesso | null;
  nome?: string | null;
  sobrenome?: string | null;
  documento?: string | null;
  telefone?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IFuncionario> = {};
