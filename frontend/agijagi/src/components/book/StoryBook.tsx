import React, { useCallback, useRef, useState, useEffect } from 'react';
import HTMLFlipBook from 'react-pageflip';
import styled from '@emotion/styled';
import Button from '../common/Button';
import theme from '../../styles/theme';
// 임의로 동화 이미지 import
import PageImg1 from '../../assets/bookcontent/pageImg1.png';
import PageImg2 from '../../assets/bookcontent/pageImg2.png';
import PageImg3 from '../../assets/bookcontent/pageImg3.png';
import PageImg4 from '../../assets/bookcontent/pageImg4.png';
import PageImg5 from '../../assets/bookcontent/pageImg5.png';
import PageImg6 from '../../assets/bookcontent/pageImg6.png';
import PageImg7 from '../../assets/bookcontent/pageImg7.png';
import PageImg8 from '../../assets/bookcontent/pageImg8.png';
import PageImg9 from '../../assets/bookcontent/pageImg9.png';
import PageImg10 from '../../assets/bookcontent/pageImg10.png';

// 동화 예시 - 추후 데이터로 받아올 예정
const storyBook = {
  cover: PageImg1,
  story: [
    '옛날 옛적에 다운이라는 아이가 있었어요.',
    '다운이는 2살 3개월로, 호기심 많은 아이였죠.',
    '3월 15일, 다운이가 처음 말을 했어요.',
    '말이 마법처럼 나와서 온 세상이 놀랐답니다.',
    '그날 이후 다운이는 동물들과 대화를 시작했어요.',
    '3월 16일, 다운이가 첫 발을 내딛었어요.',
    '그 발걸음이 마치 구름을 걷는 것 같았죠.',
    '그 후로 다운이는 숲속 친구들과 모험을 떠났어요.',
    '나무 요정들과 놀며 세상을 배우기 시작했어요.',
    '다운이는 계속 자라며 더 큰 모험을 꿈꾸었답니다.',
  ],
  image: [
    PageImg1,
    PageImg2,
    PageImg3,
    PageImg4,
    PageImg5,
    PageImg6,
    PageImg7,
    PageImg8,
    PageImg9,
    PageImg10,
  ],
};

const BookWrapper = styled.div`
  display: flex;
  width: 100%;
  height: 45vh;
  position: relative;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  @media (min-width: 700px) {
    margin: 0 auto;
    width: 100vh;
    height: 100vh;
  }
`;

const BackButtonContainer = styled.div`
  width: 90%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
`;

const BookContainer = styled.div`
  width: 90%;
  max-width: 450px;
  animation: smoothAppear 0.6s ease-in-out;

  @keyframes smoothAppear {
    from {
      opacity: 0;
      transform: translateY(-10%);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
`;

const Page = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  box-shadow: 10px 15px 10px rgba(0, 0, 0, 0.2);
  background-color: ${theme.color.primary[100]};
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: ${theme.typography.fontSize.xs};
`;

const PageImg = styled.img`
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 10px;
`;

const PageText = styled.div`
  position: absolute;
  top: 10%;
  border-radius: 10px;
  padding: 5px 8px;
  margin: 0 14px;
  color: ${theme.color.greyScale[900]};
  background-color: rgba(255, 255, 255, 0.4); // 동화 text 배경 투명도 조절 가능
  font-size: ${theme.typography.fontSize.sm};
`;

const PageNumber = styled.div`
  position: absolute;
  top: 90%;
  left: 50%;
  color: ${theme.color.greyScale[900]};
  font-size: ${theme.typography.fontSize.xs};

  @media (min-width: 700px) {
    top: 85%;
  }
`;

const PageButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
  align-items: center;
`;

const PageInfo = styled.div`
  background-color: ${theme.color.tertiary[800]};
  border-radius: 20px;
  font-size: ${theme.typography.fontSize.sm};
  color: #ffffff;
  padding: 7px 10px;
`;

const WarningMessage = styled.div`
  position: fixed;
  width: 180px;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: ${theme.color.danger[500]};
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  z-index: 1000;
  font-size: ${theme.typography.fontSize.sm};
`;

interface BookPageProps {
  number: number;
  img: string;
  children: React.ReactNode;
}

interface BookCoverProps {
  img: string;
}

interface FlipEvent {
  data: number; // 현재 페이지를 나타내는 숫자
}

const BookPage = React.forwardRef<HTMLDivElement, BookPageProps>(
  (props, ref) => {
    return (
      <Page ref={ref}>
        {/* ref required */}
        <PageImg src={props.img} style={{ opacity: '0.7' }}></PageImg>
        <PageText>{props.children}</PageText>
        <PageNumber>{props.number}</PageNumber>
      </Page>
    );
  }
);

const BookCover = React.forwardRef<HTMLDivElement, BookCoverProps>(
  (props, ref) => {
    return (
      <Page ref={ref}>
        {/* ref required */}
        <PageImg src={props.img}></PageImg>
      </Page>
    );
  }
);

BookPage.displayName = 'BookPage'; // forwardRef 사용 시 displayName 설정 권장

interface Book {
  id: number;
  image: string;
  title: string;
  start: string;
  end: string;
  page: number;
}

interface StoryBookProps {
  book: Book;
  goBack: () => void;
}

interface HTMLDivElementWithVendorPrefix extends HTMLDivElement {
  mozRequestFullscreen: () => void;
  webkitRequestFullscreen: () => void;
}

const BookComponent = ({ book, goBack }: StoryBookProps) => {
  const [isFullScreen, setIsFullScreen] = useState<boolean>(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [warning, setWarning] = useState<string>('');
  const totalPages = book.page;

  // @ts-ignore
  const mybook = useRef<HTMLFlipBook | null>(book);
  const bookContainer = useRef<HTMLDivElement>(null);

  const onFlip = useCallback((e: FlipEvent) => {
    // const totalPageCount = mybook.current?.pageFlip().getPageCount() ?? 0;
    setCurrentPage(e.data);
  }, []);

  const toggleFullscreen = () => {
    if (!bookContainer.current) {
      return;
    }

    if (bookContainer.current.requestFullscreen) {
      bookContainer.current.requestFullscreen();
    } else if (
      (bookContainer.current as HTMLDivElementWithVendorPrefix)
        .mozRequestFullscreen
    ) {
      (
        bookContainer.current as HTMLDivElementWithVendorPrefix
      ).mozRequestFullscreen();
    } else if (
      (bookContainer.current as HTMLDivElementWithVendorPrefix)
        .webkitRequestFullscreen
    ) {
      (
        bookContainer.current as HTMLDivElementWithVendorPrefix
      ).webkitRequestFullscreen();
    }

    if (window.screen.orientation && window.screen.orientation.lock) {
      window.screen.orientation.lock('landscape').catch((error) => {
        console.error(error);
      });
    } else {
      setWarning('이 브라우저는 전체화면 보기를 지원하지 않습니다.');
      setTimeout(() => setWarning(''), 3000); // 3초 후 경고 메시지 숨김
    }
  };

  useEffect(() => {
    const handleFullScreenChange = () => {
      console.log(document.fullscreenElement);
      if (document.fullscreenElement) {
        setIsFullScreen(true);
        return;
      }

      setIsFullScreen(false);
    };

    document.addEventListener('fullscreenchange', handleFullScreenChange);

    return () => {
      document.removeEventListener('fullscreenchange', handleFullScreenChange);
    };
  }, [setIsFullScreen]);

  return (
    <BookWrapper>
      <BackButtonContainer>
        <Button color="secondary" size="sm" onClick={goBack}>
          목록보기
        </Button>
        <Button color="secondary" size="sm" onClick={toggleFullscreen}>
          전체화면
        </Button>
      </BackButtonContainer>

      <BookContainer ref={bookContainer}>
        {/* @ts-ignore */}
        <HTMLFlipBook
          ref={mybook}
          size={'stretch'}
          width={isFullScreen ? 740 : 360}
          height={isFullScreen ? 720 : 500}
          drawShadow={false}
          usePortrait={false}
          showCover={true}
          onFlip={onFlip}
          key={isFullScreen ? 'landscape' : 'portrait'}
        >
          <BookCover img={book.image}></BookCover>
          {storyBook.story.map((pageText, index) => (
            <BookPage
              key={index}
              number={index + 1}
              img={storyBook.image[index]}
            >
              {pageText}
            </BookPage>
          ))}
          <BookCover img={book.image}></BookCover>
        </HTMLFlipBook>
      </BookContainer>

      <PageButtonWrapper>
        <Button
          size="sm"
          color="primary"
          onClick={() => mybook.current?.pageFlip().flipPrev()}
        >
          {'<'}
        </Button>

        {currentPage === 0 && <PageInfo>{totalPages} pages</PageInfo>}
        {currentPage !== 0 && currentPage - 1 != totalPages && (
          <PageInfo>
            {currentPage} - {currentPage + 1}
          </PageInfo>
        )}
        {currentPage - 1 == totalPages && <PageInfo>End</PageInfo>}

        <Button
          size="sm"
          color="primary"
          onClick={() => mybook.current?.pageFlip().flipNext()}
        >
          {'>'}
        </Button>
      </PageButtonWrapper>

      {warning && <WarningMessage>{warning}</WarningMessage>}
    </BookWrapper>
  );
};

export default BookComponent;
