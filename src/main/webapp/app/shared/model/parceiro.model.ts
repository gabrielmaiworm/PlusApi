import { IEndereco } from 'app/shared/model/endereco.model';
import { IContaBancaria } from 'app/shared/model/conta-bancaria.model';
import { IUser } from 'app/shared/model/user.model';

export interface IParceiro {
  id?: number;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
  cnpj?: string | null;
  inscricaoEstadual?: string | null;
  email?: string | null;
  telefone?: string | null;
  tipoServico?: string | null;
  nomeGestor?: string | null;
  sobrenomeGestor?: string | null;
  documentoGestor?: number | null;
  telefoneGestor?: string | null;
  endereco?: IEndereco | null;
  contaBancaria?: IContaBancaria | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IParceiro> = {};
