import { css } from '@emotion/react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';

export const InnerBox = styled.div<{ loginMode: boolean; height: number }>(
  (props) => css`
    display: flex;
    justify-content: center;
    height: 40%;
    max-height: 320px;
    aspect-ratio: 1;
    align-items: center;
    position: absolute;
    overflow-x: scroll;
    overflow-y: hidden;
    scroll-snap-type: x mandatory;
    -webkit-overflow-scrolling: touch;
    border-radius: 1rem;
    scrollbar-width: none; /* Firefox에서 스크롤바 숨김 */
    &::-webkit-scrollbar {
      display: none; /* Chrome, Safari에서 스크롤바 숨김 */
    }
    top: calc(7%);
    transition: all 0.75s;
    transform: translate(0, ${!props.loginMode ? 0 : props.height * -0.5}px);
  `
);

export const SlideWrapper = styled.div(
  () => css`
    display: flex;
    width: auto;
    max-width: 320px;
    scroll-snap-align: center;
  `
);

export const MediaBox = styled.div<{ isActive: boolean; loginMode: boolean }>(
  (props) => css`
    flex-shrink: 0;
    width: 100%;
    max-width: 500px;
    position: relative;
    scroll-snap-align: center;
    scroll-snap-stop: always;
    transition: all 0.75s;
    opacity: ${props.isActive && !props.loginMode ? 1 : 0};
  `
);

export const Img = styled.img(
  () => css`
    width: 100%;
    height: 100%;
    position: relative;
    z-index: 2;
  `
);

export const Gradient = styled.div(
  () => css`
    position: absolute;
    width: 100%;
    height: 100%;
    border-radius: 100%;
    aspect-ratio: 1;
    background: rgb(255, 255, 255);
    background: radial-gradient(
      circle,
      rgba(255, 255, 255, 1) 0%,
      rgba(255, 255, 255, 0) 75%
    );
    z-index: 1;
  `
);

export const LevelCircle = styled.div<{ isActive: boolean }>(
  (props) => css`
    width: 10px;
    height: 10px;
    margin: 0 5px;
    border-radius: 50%;
    background-color: ${props.isActive
      ? theme.color.primary[500]
      : theme.color.greyScale[500]};
    transition: background-color 0.3s ease;
  `
);

export const LevelIndicatorWrapper = styled.div<{
  loginMode: boolean;
  width: number;
}>(
  (props) => css`
    position: absolute;
    display: flex;
    justify-content: center;
    z-index: 1;
    transition: all 0.75s;
    transform: translate(${!props.loginMode ? 0 : props.width * -2}px, 0);
    top: calc(85% - 10rem);
  `
);

export const TypographyContainer = styled.div<{ loginMode: boolean }>(
  (props) => css`
    position: fixed;
    width: 100%;
    text-align: center;
    z-index: 3;
    top: 54%;
    padding-top: 1rem;
    overflow: visible;
    transition: opacity 0.75s;
    opacity: ${props.loginMode ? 0 : 1};
  `
);
