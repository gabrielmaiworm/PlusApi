import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { IInfoAdicional } from 'app/shared/model/info-adicional.model';

export interface IAvaliacao {
  id?: number;
  comentario?: string | null;
  nota?: number | null;
  imagemContentType?: string | null;
  imagem?: string | null;
  acessoEntradaPrincipal?: boolean | null;
  acessoBanheiro?: boolean | null;
  acessoAudioVisual?: boolean | null;
  acessoCirculacaoInterna?: boolean | null;
  acessoEstacionamento?: boolean | null;
  acessoCalcada?: boolean | null;
  estabelecimento?: IEstabelecimento | null;
  infoAdicional?: IInfoAdicional | null;
}

export const defaultValue: Readonly<IAvaliacao> = {
  acessoEntradaPrincipal: false,
  acessoBanheiro: false,
  acessoAudioVisual: false,
  acessoCirculacaoInterna: false,
  acessoEstacionamento: false,
  acessoCalcada: false,
};
