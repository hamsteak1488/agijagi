import { useEffect, useRef } from 'react';
import Int1 from '../../assets/images/login/introduction1.png';
import Int2 from '../../assets/images/login/introduction2.png';
import Int3 from '../../assets/images/login/introduction3.png';
import Typhography from '../common/Typography';
import * as s from './IntroductionSlider.style';

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
  const slides = [Int1, Int2, Int3];

  const descriptions = [
    '아이의 소중한 일상을 기록',
    '동화로 만나는 우리 아이',
    '복잡한 일정도 간편하게',
  ];

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

    slider.addEventListener('scroll', handleScroll);

    return () => {
      slider.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <>
      <s.InnerBox ref={sliderRef} loginMode={loginMode} height={height}>
        <s.SlideWrapper>
          {slides.map((slide, index) => (
            <s.MediaBox
              key={index}
              isActive={index === level}
              loginMode={loginMode}
            >
              <s.Gradient />
              <s.Img src={slide} alt={`Slide ${index + 1}`} />
            </s.MediaBox>
          ))}
        </s.SlideWrapper>
      </s.InnerBox>

      <s.TypographyContainer loginMode={loginMode}>
        <Typhography size="2xl" color="tertiary" shade="900" weight="bold">
          {descriptions[level]}
        </Typhography>
      </s.TypographyContainer>

      <s.LevelIndicatorWrapper loginMode={loginMode} width={width}>
        {slides.map((_, index) => (
          <s.LevelCircle key={index} isActive={index === level} />
        ))}
      </s.LevelIndicatorWrapper>
    </>
  );
};
