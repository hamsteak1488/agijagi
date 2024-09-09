import colorPalette from '../styles/colorPalette';

export type Size = 'xs' | 'sm' | 'md' | 'lg' | 'xl';

export type FontWeight = 'regular' | 'bold' | 'extraBold';

export type Palette =
  | 'primary'
  | 'secondary'
  | 'tertiary'
  | 'success'
  | 'danger'
  | 'greyScale';

export type PaletteColor = typeof colorPalette.red;

export type Theme = {
  palette: Object;
  typography: {
    fontSize: Record<Size, string>;
    fontWeight: Record<FontWeight, number>;
  };
  color: {
    [key in Palette]: PaletteColor;
  };
};
