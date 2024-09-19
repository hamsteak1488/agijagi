import React, { useCallback, useRef, useState } from "react";
import HTMLFlipBook from "react-pageflip";
import styled from "@emotion/styled";
import Button from "../common/Button";
import theme from "../../styles/theme";
// 임의로 동화 이미지 import
import PageImg2 from "../../assets/bookcontent/pageImg2.png";
import PageImg3 from "../../assets/bookcontent/pageImg3.png";
import PageImg4 from "../../assets/bookcontent/pageImg4.png";
import PageImg5 from "../../assets/bookcontent/pageImg5.png";

const BookWrapper = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  position: relative;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const BackButton = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-start;
  margin-left: 40px;
  margin-bottom: 20px;
`;

const Page = styled.div`
  position: absolute;
  width: 160px;
  height: 230px;
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
  width: 160px;
  height: 230px;
  border-radius: 10px;
`;

const PageText = styled.div`
  position: absolute;
  top: 10%;
  border-radius: 20px;
  padding: 5px 8px;
  margin: 0 15px;
  color: ${theme.color.greyScale[900]};
  background-color: ${theme.color.greyScale[50]};
  font-size: ${theme.typography.fontSize.sm};
`;

// const PageNumber = styled.div`
//   position: absolute;
//   top: 90%;
//   left: 50%;
//   width: 18px;
//   height: 18px;
//   text-align: center;
//   border-radius: 50%;
//   background-color: ${theme.color.tertiary[800]};
//   color: ${theme.color.greyScale[50]};
//   font-size: ${theme.typography.fontSize.xs};
// `;

const ButtonWrapper = styled.div`
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

// BookPage 컴포넌트에 타입 추가
const BookPage = React.forwardRef<HTMLDivElement, BookPageProps>(
  (props, ref) => {
    return (
      <Page className="demoPage" ref={ref}>
        {/* ref required */}
        <PageImg src={props.img} style={{opacity: "0.7"}}></PageImg>
        <PageText>{props.children}</PageText>
        {/* <PageNumber>{props.number}</PageNumber> */}
      </Page>
    );
  }
);

const BookCover = React.forwardRef<HTMLDivElement, BookCoverProps>(
  (props, ref) => {
    return (
      <Page className="demoPage" ref={ref}>
        {/* ref required */}
        <PageImg src={props.img}></PageImg>
      </Page>
    );
  }
);

BookPage.displayName = "BookPage"; // forwardRef 사용 시 displayName 설정 권장

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

const BookComponent = ({book, goBack} : StoryBookProps ) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(book.page);

  // @ts-ignore
  const mybook = useRef<HTMLFlipBook | null>(book);
  
  const onFlip = useCallback((e: FlipEvent) => {
    setCurrentPage(e.data)
    setTotalPages(mybook.current.pageFlip().getPageCount())
     
  }, []);

  return (
    <BookWrapper>
      <BackButton>
        <Button color="secondary" size="sm" onClick={goBack}>목록보기</Button>
      </BackButton>
      
      {/* @ts-ignore */}
      <HTMLFlipBook
        ref={mybook}
        width={160}
        height={230}
        drawShadow={false}
        usePortrait={false}
        showCover={true}
        onFlip={onFlip}
      >
        <BookCover img={book.image}></BookCover>
        <BookPage number={1} img={PageImg2}>
         동화 내용이 들어가는 자리
        </BookPage>
        <BookPage number={2} img={PageImg3}>
          무슨 동화일까요
        </BookPage>
        <BookPage number={3} img={PageImg4}>
          궁금하당
        </BookPage>
        <BookPage number={4} img={PageImg5}>
          재밌을까
        </BookPage>
        <BookCover img={book.image}></BookCover>
      </HTMLFlipBook>

      <ButtonWrapper>
        <Button size="sm" color="primary" onClick={() => mybook.current?.pageFlip().flipPrev()}>
          {"<"}
        </Button>
        <PageInfo>{currentPage} of {totalPages}</PageInfo>
        <Button size="sm" color="primary" onClick={() => mybook.current?.pageFlip().flipNext()}>
          {">"}
        </Button>
      </ButtonWrapper>
    </BookWrapper>
  );
};

export default BookComponent;
