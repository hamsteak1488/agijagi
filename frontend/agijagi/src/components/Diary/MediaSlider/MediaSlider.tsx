import { useState } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../../styles/theme';
import MediaInput from '../MediaInput/MediaInput';
import Button from '../../common/Button';
import Typhography from '../../common/Typhography';

interface MediaSliderProps {
  fileList?: File[];
  isWriteMode: boolean;
}

const Container = styled.div(
  () => css`
    display: flex;
    justify-content: center;
    align-items: center;
  `
);

const DelIcon = styled.div(
  () => css`
    position: absolute;
    width: 20px;
    height: 20px;
  `
);

const SliderWrapper = styled.div(
  () => css`
    display: flex;
    width: 100%;
  `
);

const IndexLabel = styled.div(
  () => css`
    position: absolute;
    width: 40px;
    height: 20px;
    background-color: ${theme.color.primary[200]};
    left: 5%;
    top: 2%;
    border-radius: 1rem;
    text-align: center;
  `
);

const MediaBox = styled.div(
  () => css`
    position: relative;
    display: flex;
    text-align: center;
    width: 80%;
    height: 80%;
    max-width: 500px;
    max-height: 500px;
    aspect-ratio: 1;
    scroll-snap-align: center;
    scroll-snap-type: x proximity;
    background-color: #f0f0f0;
  `
);

export const MediaSlider = ({
  fileList = [],
  isWriteMode,
}: MediaSliderProps) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const handleNext = () => {
    if (currentIndex < fileList.length) {
      setCurrentIndex((prevIndex) => prevIndex + 1);
    }
  };

  const handlePrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex((prevIndex) => prevIndex - 1);
    }
  };

  const renderMedia = () => {
    return fileList.map((file, index) => (
      <MediaBox key={index}>
        <IndexLabel>
          <Typhography size="xs" color="white">
            {index + 1}/{fileList.length}
          </Typhography>
        </IndexLabel>
        {file.type.startsWith('image/') ? (
          <img
            src={URL.createObjectURL(file)}
            alt={`media-${index}`}
            style={{ width: '100%', height: '100%', objectFit: 'cover' }}
          />
        ) : file.type.startsWith('video/') ? (
          <video controls style={{ width: '100%', height: '100%' }}>
            <source src={URL.createObjectURL(file)} />
          </video>
        ) : (
          <p>지원하지 않는 미디어 타입</p>
        )}
      </MediaBox>
    ));
  };

  return (
    <Container>
      <SliderWrapper>{renderMedia()}</SliderWrapper>
    </Container>
  );
};

export default MediaSlider;
