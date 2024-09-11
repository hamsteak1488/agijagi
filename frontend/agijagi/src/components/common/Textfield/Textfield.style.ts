import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../../styles/theme';
import { TextfieldColor } from './Textfield.types';

export const TextfieldContainer = styled.div<{ color: TextfieldColor }>(
  (props) => css`
    position: relative;
    display: flex;
    height: 40px;
    border-radius: 15px 15px 0px 0px;
    background-color: ${theme.color[props.color][200]};
    border-bottom: 3px solid ${theme.color[props.color][900]};
  `
);

export const Label = styled.div<{
  isFloating: boolean;
}>(
  (props) => css`
    position: absolute;
    font-size: ${props.isFloating ? `0.625rem` : `1.125rem`};
    font-weight: bold;
    transition: all 0.3s;
    transform: ${props.isFloating && `translateY(-12px)`};
    left: 12px;
    bottom: 12px;
  `
);

export const Textfield = styled.input(
  css`
    margin: 10px 0px 0px 10px;
    width: 100%;
    height: 100%;
    background: transparent;
    border: none;
    font-size: 1rem;
    :focus {
      outline: none;
    }
  `
);
