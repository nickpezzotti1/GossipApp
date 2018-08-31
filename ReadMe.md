#Read me
GossipApp is an Android app that allows anonymous users to share anonymous geo-tagged text-posts. These post live for 24 hours, before being deleted. Anyone can log onto the app and view all posts in his proximity.
Think of it a mash-up of Snapchat and the now deceased Yik Yak.

##Bugs & Fixes:
1. The database is public, anyone can read and write to it 
2. The posts are filtered on the user side of the application, asking for the server for the timestamp of each of the posts. A listener design pattern, would not only entail looser coupling between the modules but would prevent attacks on the user side (for example not filtering to only the posts we want to display)
3. You can change the time on your phone to the future to make your post never disappear. Setting up a server with internal time, would be in my opinion the necessary future step.

## Ideas for future implementations:
1. Adding an image (from camera or from gallery)
2. Only allow to post from your current location
3. Having a thumbs-up mechanism that increments a post’s lifetime or noticeability:
4. (Monetization idea) Watch ads/pay to increment a post by 30 minutes
5. Have comments on each post, “reddit-style”
6. Have different channels based on university/events
