const path = require("path");

const src = path.resolve(__dirname, "../../..", "src", "main", "resources", "webapp");

const config = {
  entry: path.resolve(src, "starterkit", "index.js"),
  output: "bundle.js",
  websiteTitle: "Starterkit",
  devServerPort: 9000
};


const VueLoaderPlugin = require("vue-loader/lib/plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  mode: "production",
  entry: config.entry,
  output: {
    path: src,
    filename: config.output
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: "vue-loader"
      },
      {
        test: /\.js$/,
        use: {
          loader: "babel-loader",
          options: {
            presets: ["@babel/preset-env"]
          }
        }
      },
      {
        test: /\.css$/,
        use: [
          "vue-style-loader",
          "style-loader",
          "css-loader"
        ]
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new HtmlWebpackPlugin({
      title: config.websiteTitle
    })
  ],
  performance: {
    maxEntrypointSize: 500000,
    maxAssetSize: 500000
  },
  devServer: {
    contentBase: src,
    port: config.devServerPort,
    inline: true,
    hot: true
  }
};