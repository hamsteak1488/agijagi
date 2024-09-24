import { useState, useRef, useEffect } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import Int1 from '../../assets/images/login/introduction1.png';
import Int2 from '../../assets/images/login/introduction2.png';
import Int3 from '../../assets/images/login/introduction3.png';

export const InnerBox = styled.div(
  () => css`
    display: flex;
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

export const SlideWrapper = styled.div(
  () => css`
    display: flex;
    width: auto;
    max-width: 320px;
    scroll-snap-align: center;
  `
);

export const MediaBox = styled.div(
  () => css`
    flex-shrink: 0;
    width: 320px;
    max-width: 500px;
    overflow: hidden;
    position: relative;
    scroll-snap-align: center;
    scroll-snap-stop: always;
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
  `
);

export const Inform = styled.div(
  () => css`
    z-index: 1;
  `
);

export const IntroductionSlider = () => {
  const [currentIndex, setCurrentIndex] = useState<number>(0); // 슬라이드 인덱스의 타입을 number로 지정
  const sliderRef = useRef<HTMLDivElement | null>(null); // ref에 들어가는 값이 HTMLDivElement이거나 null임을 명시

  // 슬라이드 이미지 배열
  const slides: string[] = [Int1, Int2, Int3];

  useEffect(() => {
    const slider = sliderRef.current;
    if (!slider) return; // slider가 존재하지 않으면 함수 종료

    const handleScroll = () => {
      const scrollPosition = slider.scrollLeft; // 현재 스크롤 위치
      const slideWidth = slider.offsetWidth; // 슬라이드의 너비
      const newIndex = Math.round(scrollPosition / slideWidth); // 현재 인덱스 계산
      setCurrentIndex(newIndex); // 인덱스 업데이트
    };

    // scroll 이벤트 리스너 추가
    slider.addEventListener('scroll', handleScroll);

    return () => {
      slider.removeEventListener('scroll', handleScroll); // 컴포넌트 언마운트 시 이벤트 리스너 제거
    };
  }, []);

  return (
    <div>
      <InnerBox ref={sliderRef}>
        <SlideWrapper>
          {slides.map((slide, index) => (
            <MediaBox key={index}>
              <Img src={slide} alt={`Slide ${index + 1}`} />{' '}
              {/* alt 속성 추가 */}
            </MediaBox>
          ))}
        </SlideWrapper>
      </InnerBox>
      {/* 현재 슬라이드 인덱스 표시 */}
      <Inform>
        현재 슬라이드: {currentIndex + 1} / {slides.length}
      </Inform>
    </div>
  );
};
