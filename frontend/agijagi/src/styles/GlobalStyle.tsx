import { Global, css } from '@emotion/react';

import NPSFontRegular from '../assets/fonts/NPSfont_regular.woff2';
import NPSFontBold from '../assets/fonts/NPSfont_bold.woff2';
import NPSFontExtraBold from '../assets/fonts/NPSfont_extrabold.woff2';
import OmyuPretty from '../assets/fonts/omyu_pretty.ttf';

import theme from './theme';

const GlobalStyle = () => {
  return (
    <Global
      styles={[
        css`
          @font-face {
            font-family: 'NPSFont';
            font-weight: 500;
            src: url(${NPSFontRegular});
          }

          @font-face {
            font-family: 'NPSFont';
            font-weight: 700;
            src: url(${NPSFontBold});
          }

          @font-face {
            font-family: 'NPSFont';
            font-weight: 800;
            src: url(${NPSFontExtraBold});
          }

          @font-face {
            font-family: 'OmyuFont';
            src: url(${OmyuPretty}) format('truetype');
          }

          * {
            font-family: 'NPSFont';
            font-size: 16px;
            outline: none;
            -webkit-tap-highlight-color: transparent !important;

            ::-webkit-scrollbar {
              width: 0;
              height: 0;
            }
          }

          body,
          #root {
            width: 100vw;
            height: calc(var(--vh) * 100);
            margin: 0;
            overflow-x: hidden;
            overflow-y: scroll;
            -ms-overflow-style: none;
            background-color: ${theme.color.primary[100]};
            overscroll-behavior: none;

            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
              -webkit-appearance: none;
              margin: 0;
            }
          }
        `,
      ]}
    />
  );
};

export default GlobalStyle;
