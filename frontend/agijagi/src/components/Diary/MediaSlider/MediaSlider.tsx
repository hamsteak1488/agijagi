import { useEffect, useRef, useState } from 'react';
import Typhography from '../../common/Typography';
import MediaInput from '../MediaInput';
import {
  Container,
  DelIcon,
  DelIconDiv,
  IndexLabel,
  InnerBox,
  MediaBox,
  SlideWrapper,
} from './MediaSlider.styles';

interface MediaSliderProps {
  fileList?: File[];
  urlList: string[];
  urlType: string[];
  isWriteMode: boolean;
  handleUpload?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  handleDelete?: (index: number) => void;
  handleUrlDelete?: (index: number) => void;
  removedList?: string[];
  isInitialRender: boolean;
}

export const MediaSlider = ({
  fileList = [],
  urlList = [],
  urlType = [],
  isWriteMode,
  handleUpload = () => {},
  handleDelete = () => {},
  handleUrlDelete = () => {},
  isInitialRender = true,
}: MediaSliderProps) => {
  const scrollContainerRef = useRef<HTMLDivElement | null>(null);
  const [uploadKey, setUploadKey] = useState<number>(0);

  const handleWheel = (e: React.WheelEvent) => {
    if (scrollContainerRef.current) {
      scrollContainerRef.current.scrollLeft += e.deltaY;
    }
  };

  const handleScroll = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (handleUpload) handleUpload(e);
    setUploadKey(uploadKey + 1);
  };

  useEffect(() => {
    if (
      scrollContainerRef.current &&
      (fileList.length > 0 || urlList.length > 0) &&
      isWriteMode
    ) {
      if (isInitialRender) {
        scrollContainerRef.current.scrollTo({
          left: 5000,
          behavior: 'smooth',
        });
      } else {
        scrollContainerRef.current.scrollTo({
          left:
            scrollContainerRef.current.scrollWidth -
            (window.innerWidth < 500 ? window.innerWidth * 2 : 1000),
          behavior: 'smooth',
        });
      }
    }
  }, [uploadKey, isWriteMode, isInitialRender]);

  const renderMedia = () => {
    return (
      <>
        {urlList.map((url, index) => (
          <MediaBox key={`url-${index}`}>
            <IndexLabel>
              <Typhography size="xs" color="white">
                {index + 1}/{urlList.length + fileList.length}
              </Typhography>
            </IndexLabel>
            {isWriteMode && (
              <DelIconDiv onClick={() => handleUrlDelete(index)}>
                {DelIcon}
              </DelIconDiv>
            )}
            {urlType[index] === 'image' ? (
              <img
                src={url}
                alt={`media-url-${index}`}
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
              />
            ) : (
              <video controls style={{ width: '100%', height: '100%' }}>
                <source src={url} />
              </video>
            )}
          </MediaBox>
        ))}

        {fileList.map((file, index) => (
          <MediaBox key={`file-${index}`}>
            <IndexLabel>
              <Typhography size="xs" color="white">
                {urlList.length + index + 1}/{urlList.length + fileList.length}
              </Typhography>
            </IndexLabel>
            {isWriteMode && (
              <DelIconDiv onClick={() => handleDelete(index)}>
                {DelIcon}
              </DelIconDiv>
            )}
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
            <MediaInput handleUpload={handleScroll}></MediaInput>
          </MediaBox>
        )}
      </>
    );
  };

  return (
    <Container>
      <InnerBox ref={scrollContainerRef} onWheel={handleWheel}>
        <SlideWrapper>{renderMedia()}</SlideWrapper>
      </InnerBox>
    </Container>
  );
};

export default MediaSlider;
