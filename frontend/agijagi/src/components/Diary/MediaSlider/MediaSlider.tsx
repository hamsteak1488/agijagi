import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../../styles/theme';
import MediaInput from '../MediaInput/MediaInput';
import Typhography from '../../common/Typhography';

interface MediaSliderProps {
  fileList?: File[];
  isWriteMode: boolean;
}

const InnerBox = styled.div(
  () => css`
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    overflow-x: scroll;
    scroll-snap-type: x mandatory;
    -webkit-overflow-scrolling: touch;
    scrollbar-width: none; /* Firefox에서 스크롤바 숨김 */
    &::-webkit-scrollbar {
      display: none; /* Chrome, Safari에서 스크롤바 숨김 */
    }
  `
);

const SlideWrapper = styled.div(
  () => css`
    display: flex;
    width: 100%;
    max-width: 500px;
    scroll-snap-align: center;
    scroll-snap-stop: always;
  `
);

const MediaBox = styled.div(
  () => css`
    flex-shrink: 0;
    width: 100%;
    max-width: 500px;
    aspect-ratio: 1;
    overflow: hidden;
    background-color: #f0f0f0;
    position: relative;
    scroll-snap-align: center;
  `
);

export const Container = styled.div(
  () => css`
    padding: 2rem;
    max-width: 500px;
    max-height: 500px;
    border-radius: 1rem;
  `
);

const IndexLabel = styled.div(
  () => css`
    position: absolute;
    width: 40px;
    height: 20px;
    background-color: ${theme.color.primary[200]};
    left: 4%;
    top: 4%;
    border-radius: 1rem;
    text-align: center;
  `
);

export const MediaSlider = ({
  fileList = [],
  isWriteMode,
}: MediaSliderProps) => {
  const renderMedia = () => {
    return (
      <>
        {fileList.map((file, index) => (
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
        ))}
        {isWriteMode && (
          <MediaBox>
            <MediaInput></MediaInput>
          </MediaBox>
        )}
      </>
    );
  };

  return (
    <Container>
      <InnerBox>
        <SlideWrapper>{renderMedia()}</SlideWrapper>
      </InnerBox>
    </Container>
  );
};

export default MediaSlider;
