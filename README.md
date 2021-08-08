# Memes App

With Daily Memes App, you can get the funniest memes and share them with your friends through your favorite apps and also download the memes.

## Overview
A meme is a virally transmitted image embellished with text, usually sharing pointed commentary on cultural symbols, social ideas, or current events. A meme is typically a photo or video, although sometimes it can be a block of text. When a meme resonates with many people, it's spread via social platforms like Twitter, Facebook, Instagram, texting, and more. The more a meme is spread, the greater the cultural influence it has.
There's an endless variety of memes, ranging from mundane, everyday topics to critical life and world events. More are created and shared every day, and new material is constantly available.

## Lessons Learned

- Learned about RecyclerViw.
- Learned about Volley Library.
- Learned about RecyclerView OnClickListener.
- Learned about requesting permission to the user to store data.

## API Reference

#### Get item

```http
  GET https://meme-api.herokuapp.com/gimme
  
```
#### Specify count (MAX 50)

```http
In order to get multiple memes in a single request specify the count with the following endpoint.
Endpoint: /gimme/{count}
Example: https://meme-api.herokuapp.com/gimme/2
```


## Support
For support, email - "singhharendra711@gmail.com".


## Contributing
Contributions are always welcome!
- You can contribute in implementing Network Callback which notify about network changes.   [Network Callback](https://developer.android.com/reference/android/net/ConnectivityManager.NetworkCallback)
- You can contribute in implementing endless RecyclerView.[Endless-RecyclerView](https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview)


## Authors
- [@harendrasingkushwaha](https://gitlab.com/harendrasinghkushwaha)


## Project status
Main aim of the Application have been completed. For future work, We can implement Network Callback that is used for notifications about network changes and also endless RecyclerView or refresh RecyclerView can method can be added so that user can get new memes when he have gone through all fethched memes.
