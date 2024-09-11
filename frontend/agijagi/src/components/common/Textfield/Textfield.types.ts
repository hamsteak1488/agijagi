import { Palette } from '../../../types/theme';

export type TextfieldColor = Extract<
  Palette,
  'primary' | 'secondary' | 'tertiary' | 'greyscale'
>;
