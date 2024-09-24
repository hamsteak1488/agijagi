import { useState, useRef, useEffect } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import Int1 from '../../assets/images/login/introduction1.png';
import Int2 from '../../assets/images/login/introduction2.png';
import Int3 from '../../assets/images/login/introduction3.png';

export const InnerBox = styled.div(
  () => css`
    display: flex;
    height: 100%;
    justify-content: center;
    align-items: center;
    position: relative;
    overflow-x: scroll;
    scroll-snap-type: x mandatory;
    -webkit-overflow-scrolling: touch;
    border-radius: 1rem;
    scrollbar-width: none; /* Firefox에서 스크롤바 숨김 */
    &::-webkit-scrollbar {
      display: none; /* Chrome, Safari에서 스크롤바 숨김 */
    }
  `
);

export const SlideWrapper = styled.div<{ loginMode: boolean; height: number }>(
  (props) => css`
    display: flex;
    width: auto;
    max-width: 320px;
    scroll-snap-align: center;
    transition: all 1s;
    transform: translate(0, ${!props.loginMode ? 0 : props.height * -1}px);
    top: 8%;
  `
);

export const MediaBox = styled.div<{ isActive: boolean }>(
  (props) => css`
    flex-shrink: 0;
    width: 320px;
    max-width: 500px;
    overflow: hidden;
    position: relative;
    scroll-snap-align: center;
    scroll-snap-stop: always;
    transition: all 0.75s;
    opacity: ${props.isActive ? 1 : 0};
  `
);

export const Container = styled.div(
  () => css`
    padding: 1.25rem 2rem 1.25rem 2rem;
    width: auto;
    max-width: 500px;
    max-height: 500px;
    border-radius: 1rem;
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

export const Inform = styled.div(
  () => css`
    z-index: 1;
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
    position: relative;
    display: flex;
    justify-content: center;
    margin-top: 1rem;
    z-index: 1;
    transition: all 0.75s;
    transform: translate(${!props.loginMode ? 0 : props.width * -2}px, 0);
    top: 12%;
  `
);

interface IntroductionSliderProps {
  level: number;
  handleLevel: (i: number) => void;
  loginMode: boolean;
}

export const IntroductionSlider = ({
  level,
  handleLevel,
  loginMode,
}: IntroductionSliderProps) => {
  const sliderRef = useRef<HTMLDivElement | null>(null);
  const slides: string[] = [Int1, Int2, Int3];
  const width = window.innerWidth;
  const height = window.innerHeight;

  useEffect(() => {
    const slider = sliderRef.current;
    if (!slider) return;

    const handleScroll = () => {
      const scrollPosition = slider.scrollLeft;
      const slideWidth = slider.offsetWidth;
      const newIndex = Math.round(scrollPosition / slideWidth);
      handleLevel(newIndex);
    };

    // scroll 이벤트 리스너 추가
    slider.addEventListener('scroll', handleScroll);

    return () => {
      slider.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <>
      <InnerBox ref={sliderRef}>
        <SlideWrapper loginMode={loginMode} height={height}>
          {slides.map((slide, index) => (
            <MediaBox key={index} isActive={index === level}>
              <Gradient />
              <Img src={slide} alt={`Slide ${index + 1}`} />
            </MediaBox>
          ))}
        </SlideWrapper>
      </InnerBox>
      <LevelIndicatorWrapper loginMode={loginMode} width={width}>
        {slides.map((_, index) => (
          <LevelCircle key={index} isActive={index === level} />
        ))}
      </LevelIndicatorWrapper>
    </>
  );
};
